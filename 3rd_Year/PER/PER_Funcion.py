import numpy as np; import matplotlib.pyplot as plt
R = np.linspace(-1, 1, 50); X1, X2 = np.meshgrid(R, R); X = np.c_[np.ravel(X1), np.ravel(X2)]
a = np.array([-1, -1]); Y = (np.power(X, 4) @ a).reshape(X1.shape)
fig = plt.figure(figsize=(6, 2)); ax = plt.subplot(121)
cp = ax.contourf(X1, X2, Y, 15, cmap='Blues_r'); plt.colorbar(cp, ax=ax, shrink=.8)
r = np.linspace(-1, 1, 15); x1, x2 = np.meshgrid(r, r); x = np.c_[np.ravel(x1), np.ravel(x2)]
neg_grad = -np.power(x, 3) @ np.diag(4 * a)
ax.quiver(x[:, 0], x[:, 1], neg_grad[:, 0], neg_grad[:, 1], color='C1');
ax = plt.subplot(122, projection='3d'); ax.set_xlabel('$x_1$'); ax.set_ylabel('$x_2$')
ax.set_title(rf'$y={a[0]:.1f}\,x_1^4{a[1]:+.1f}\,x_2^4$'); ax.text(0, 0, 0, '*')
ax.plot_wireframe(x1, x2, a[0]*x1**4 + a[1]*x2**4)