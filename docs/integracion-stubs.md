# Cambios a hacer en la integración

Qué hay que tocar cuando los stubs se reemplacen por las piezas reales.

| # | Cambio | Quién | Cuándo |
|---|---|---|---|
| 1 | El controlador (Tareas 10, 13, 14) se escribe contra `IVista` y `Ecosistema`; no importa nada de `stubs` | Felipe | Fase 2 |
| 2 | Las ventanas de Swing (`VentanaPrincipal`, paneles) implementan `IVista` o delegan en una clase que la implemente | Agos y Guada | Fases 3 y 4 |
| 3 | Si cambia algún método de `IVista`, actualizar `VistaStub` y avisar al resto antes de mergear | Quien lo cambie | Siempre |
| 4 | En `main`, pasar la vista real al controlador en lugar de `VistaStub` | Felipe (Tarea 14) | Integración |
| 5 | El controlador crea `Ecosistema` real; `EcosistemaStub` queda solo para pruebas | Felipe | Integración |
| 6 | Correr la simulación completa con la vista real y comparar con lo que mostraba `PruebaStubs` | Todos | Integración |
| 7 | Borrar el paquete `stubs` (`VistaStub`, `EcosistemaStub`, `PruebaStubs`) y este archivo | Felipe | Cuando el 6 funcione |