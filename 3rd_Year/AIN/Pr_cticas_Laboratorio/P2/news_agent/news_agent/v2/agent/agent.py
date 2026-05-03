# ============================================================
#   MULTI-AGENTE: Recopilador → Resumidor → Editor
#   Con NewsAPI como fuente real de noticias
#
# !!!!!!!!  pip install feedparser
# ============================================================

import os
import requests
import feedparser
from typing import List, Dict
from datetime import date

from google.adk.agents import LlmAgent, SequentialAgent, ParallelAgent
from google.adk.models.lite_llm import LiteLlm

# ============================================================
#  TOOL: fetch_news_batch_newsapi
# ============================================================

def fetch_news_google(categories: List[str]) -> Dict:
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

def fetch_news_batch_newsapi(categories: List[str]) -> Dict:
    """
    Obtiene noticias reales desde NewsAPI para varias categorías.
    Devuelve:
        { "status": "success",
          "news_by_category": { cat: [ {title, content, url}, ... ] }
        }
    """
    api_key = os.getenv("NEWSAPI_KEY")
    if not api_key:
        return {"status": "error", "error": "NEWSAPI_KEY no está definido en el .env"}

    # Mapeo de categorías humanas → categorías permitidas por NewsAPI
    allowed = {
        "technology": "technology",
        "tech": "technology",
        "tecnologia": "technology",
        "tecnología": "technology",

        "sports": "sports",
        "sport": "sports",
        "deportes": "sports",
        "deporte": "sports",

        "business": "business",
        "negocios": "business",

        "science": "science",
        "ciencia": "science",

        "health": "health",
        "salud": "health",

        "entertainment": "entertainment",
        "cultura": "entertainment",
        "arte": "entertainment",

        "general": "general",
        "politica": "general",
        "politics": "general",
    }

    results = {}

    for cat in categories:
        raw = cat.lower().strip()
        newsapi_cat = allowed.get(raw, "general")

        url = "https://newsapi.org/v2/top-headlines"
        params = {
            "apiKey": api_key,
            "category": newsapi_cat,
            "language": "en",
            "pageSize": 10,
        }

        response = requests.get(url, params=params)
        data = response.json()
        arts = data.get("articles", [])

        parsed = []
        for a in arts:
            parsed.append({
                "title": a.get("title", ""),
                "content": a.get("content") or a.get("description") or "",
                "url": a.get("url", "")
            })

        results[raw] = parsed

    return {"status": "success", "news_by_category": results}


# ============================================================
#  1) Agente RECOPILADOR
# ============================================================

buscador_newsapi = LlmAgent(
    name="buscador_newsapi",
    #model="gemini-2.0-flash",
    model=LiteLlm(model="openai/gpt-oss-120b", 
        api_base="https://api.poligpt.upv.es/", 
        api_key="sk-LFXs1kjaSxtEDgOMlPUOpA"),
    description="Busca noticias usando NewsAPI.",
    instruction=(
        "Eres el Agente Recopilador.\n"
        "Debes identificar las categorías pedidas por el usuario.\n\n"

        "IMPORTANTE:\n"
        "- Si una tool devuelve {\"status\": \"error\"} o una lista vacía,\n"
        "  NO debes volver a llamar a ninguna tool.\n"
        "- Simplemente devuelve el resultado tal como lo recibiste.\n"
        "- NO intentes corregir, reintentar o pedir otra vez.\n\n"

        "Tu única tarea es llamar UNA vez a tu tool asignada (GNews, Mediastack, etc.)\n"
        "y devolver su salida tal como está."
    ),
    tools=[fetch_news_batch_newsapi],
    output_key="newsapi_raw"
)

buscador_google = LlmAgent(
    name="buscador_google",
    #model="gemini-2.0-flash",
    model=LiteLlm(model="openai/gpt-oss-120b", 
        api_base="https://api.poligpt.upv.es/", 
        api_key="sk-LFXs1kjaSxtEDgOMlPUOpA"),
    description="Busca noticias usando Google News RSS.",
    instruction=(
        "Eres el Agente Recopilador.\n"
        "Debes identificar las categorías pedidas por el usuario.\n\n"

        "IMPORTANTE:\n"
        "- Si una tool devuelve {\"status\": \"error\"} o una lista vacía,\n"
        "  NO debes volver a llamar a ninguna tool.\n"
        "- Simplemente devuelve el resultado tal como lo recibiste.\n"
        "- NO intentes corregir, reintentar o pedir otra vez.\n\n"

        "Tu única tarea es llamar UNA vez a tu tool asignada (GNews, Mediastack, etc.)\n"
        "y devolver su salida como está."
    ),
    tools=[fetch_news_google],
    output_key="google_raw"
)


recopilador_paralelo = ParallelAgent(
    name="recopilador_paralelo",
    sub_agents=[buscador_newsapi, buscador_google],
    description="Recopila noticias desde NewsAPI y Google News en paralelo."
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
    description="Resume las noticias de ambas fuentes.",
    instruction=(
        "Tienes session.state['newsapi_raw'] y session.state['google_raw'].\n"
        "Ambos contienen news_by_category.\n\n"
        "Debes combinar TODAS las noticias de ambos orígenes.\n"
        "Para cada noticia crea:\n"
        "  {\"title\": \"..\", \"summary\": \"..\", \"url\": \"..\"}\n"
        "Usa resúmenes de 2–4 frases.\n\n"
        "Devuelve un JSON:\n"
        "{ \"items\": [ ... ] }\n"
        "Guárdalo en session.state['summaries']."
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
    name="news_pipeline_multisource",
    sub_agents=[recopilador_paralelo, resumidor, editor],
    description="Pipeline multiagente con NewsAPI + Google News."
)