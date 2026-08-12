"""Minimal PCM echo WebSocket for the Voice AI portfolio feature."""

from fastapi import FastAPI, WebSocket, WebSocketDisconnect

app = FastAPI(title="AllThingsAndroid voice echo")


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}


@app.websocket("/ws/audio")
async def audio_socket(websocket: WebSocket) -> None:
    await websocket.accept()
    try:
        while True:
            chunk = await websocket.receive_bytes()
            await websocket.send_bytes(chunk)
    except WebSocketDisconnect:
        return
