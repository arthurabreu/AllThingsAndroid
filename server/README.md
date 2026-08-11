# Voice AI demo server

Local FastAPI WebSocket that echoes PCM audio. The Android `demo` flavor falls back to in-process echo if this is down.

```bash
cd server
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
uvicorn main:app --host 0.0.0.0 --port 8765
```

Endpoint: `ws://127.0.0.1:8765/ws/audio`
