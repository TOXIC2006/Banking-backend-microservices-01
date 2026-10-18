# CI/CD setup

The repo has two GitHub Actions workflows:

- **ci.yml** — on every push to `main`: builds each service's jar (JDK 21)
  and pushes a Docker image to Docker Hub as
  `<username>/<service>:latest` and `<username>/<service>:<git-sha>`.
- **deploy.yml** — manual ("Run workflow"): copies `docker-compose.yml` to a
  server over SSH, pulls the images, and restarts the stack.

## One-time setup

### 1. GitHub secrets (repo → Settings → Secrets and variables → Actions)

| Secret | Value |
|---|---|
| `DOCKERHUB_USERNAME` | your Docker Hub username |
| `DOCKERHUB_TOKEN` | Docker Hub access token (hub.docker.com → Account Settings → Security → Access Tokens) |
| `SSH_HOST` | IP or hostname of the deploy server |
| `SSH_USER` | SSH login user (e.g. `ubuntu`) |
| `SSH_PORT` | SSH port (usually `22`) |
| `SSH_PRIVATE_KEY` | private key whose public key is in the server's `~/.ssh/authorized_keys` |

### 2. Deploy server

Any Linux box with Docker and docker compose plugin, reachable over SSH:

```bash
sudo apt update && sudo apt install -y docker.io docker-compose-v2
```

### 3. First deploy

GitHub → Actions → "Deploy" → Run workflow. The first run creates
`~/banking/.env` on the server with placeholder values — edit it there to
set the real MySQL password and Razorpay keys, then run the deploy again:

```bash
nano ~/banking/.env
```

### Notes

- `docker-compose.yml` opens MySQL on host port 3306 and Redis on 6379.
  On a machine that already runs MySQL/Redis/Kafka locally, either change
  the host-side ports in the compose file or use a separate VM.
- Kafka and Zookeeper are included in the compose file, so the whole stack
  (infra + 6 services + gateway) runs from one `docker compose up -d`.
