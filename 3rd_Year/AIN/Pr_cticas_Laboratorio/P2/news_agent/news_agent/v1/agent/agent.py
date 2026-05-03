# ============================================================
#   MULTI-AGENTE: Recopilador → Resumidor → Editor
#   Con NewsAPI como fuente real de noticias
# !!!!!!!!  pip install feedparser
#
# ============================================================

import os
import requests
import feedparser
from typing import List, Dict
from datetime import date

from google.adk.agents import LlmAgent, SequentialAgent
from google.adk.models.lite_llm import LiteLlm

# ============================================================
#  TOOL: fetch_news_batch_newsapi
# ============================================================

def fetch_news(categories: List[str]) -> Dict:
    """
    Recupera noticias desde Google News RSS para varias categorías.
    Devuelve news_by_category con title, content (summary) y url.
    """

    results = {}

    for cat in categories:
        q = cat.replace(" ", "+")
        rss_url = (
            f"https://news.google.com/rss/search?q={q}&hl=en&gl=US&ceid=US:en"
        )

        feed = feedparser.parse(rss_url)
        articles = []

        for entry in feed.entries[:10]:  # max 10 artículos
            articles.append({
                "title": entry.title,
                "content": entry.summary if "summary" in entry else "",
                "url": entry.link
            })

        results[cat] = articles

    return {"status": "success", "news_by_category": results}


# ============================================================
#  1) Agente RECOPILADOR
# ============================================================

recopilador = LlmAgent(
    name="recopilador_noticias",
    #model="gemini-2.0-flash",
    model=LiteLlm(model="openai/gpt-oss-120b", 
        api_base="https://api.poligpt.upv.es/", 
        api_key="sk-LFXs1kjaSxtEDgOMlPUOpA"),
    description="Agente que recopila noticias desde NewsAPI.",
    instruction=(
        "Eres el Agente Recopilador.\n"
        "Tu tarea es identificar qué categorías solicita el usuario.\n"
        "Las categorías válidas son: technology, sports, business, science, "
        "health, entertainment, general.\n\n"
        "Devuelve SIEMPRE una lista, aunque el usuario solo mencione una categoría.\n"
        "Luego llama a la herramienta fetch_news con esa lista.\n"
        "Debes guardar la respuesta completa en session.state['raw_news']."
    ),
    tools=[fetch_news],
    output_key="raw_news",
)


# ============================================================
#  2) Agente RESUMIDOR
# ============================================================

resumidor = LlmAgent(
    name="resumidor_noticias",
    #model="gemini-2.0-flash",
    model=LiteLlm(model="openai/gpt-oss-120b", 
        api_base="https://api.poligpt.upv.es/", 
        api_key="sk-LFXs1kjaSxtEDgOMlPUOpA"),
    description="Agente que resume noticias recopiladas.",
    instruction=(
        "Eres el Agente Resumidor.\n"
        "En session.state['raw_news'] tienes un JSON con este formato:\n\n"
        "  { 'news_by_category': { category: [ {title, content, url}, ... ] } }\n\n"
        "Debes generar un resumen para CADA noticia: 3-4 frases claras.\n"
        "Devuelve un JSON con esta forma:\n"
        "  { \"items\": [ {\"title\": \"..\", \"summary\": \"..\", \"url\": \"..\"}, ... ] }\n\n"
        "Guarda este JSON final en session.state['summaries']."
    ),
    output_key="summaries",
)


# ============================================================
#  3) Agente EDITOR
# ============================================================

editor = LlmAgent(
    name="editor_boletin",
    #model="gemini-2.0-flash",
    model=LiteLlm(model="openai/gpt-oss-120b", 
        api_base="https://api.poligpt.upv.es/", 
        api_key="sk-LFXs1kjaSxtEDgOMlPUOpA"),
    description="Agente que genera el boletín Markdown.",
    instruction=(
        "Eres el Agente Editor.\n"
        "En session.state['summaries'] tienes un JSON con resúmenes.\n\n"
        "Debes crear un boletín de noticias en formato Markdown:\n"
        "- Título general con fecha.\n"
        "- Breve introducción.\n"
        "- Lista de noticias resumidas.\n"
        "- Cada noticia debe incluir título, resumen y URL.\n"
        "- No inventes contenido.\n\n"
        "La fecha de hoy es: " + date.today().isoformat() + "\n\n"
        "Devuelve ÚNICAMENTE el Markdown final."
    ),
    output_key="bulletin_md",
)


# ============================================================
#  ROOT PIPELINE: secuencial
# ============================================================

root_agent = SequentialAgent(
    name="news_bulletin_pipeline",
    sub_agents=[recopilador, resumidor, editor],
    description="Pipeline multiagente para generar un boletín de noticias diario.",
)