#!/bin/bash

echo "Criando ambiente de teste"

if ! git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
  echo "Erro: Este diretório não possui um repositório git"
  exit 1
fi

branch_name=$(git rev-parse --abbrev-ref HEAD)
snapshot_version="${branch_name}-SNAPSHOT"

echo "[STEP 01] - construindo build"
mvn clean install -DskipTests=true

echo "[STEP 02] - Criando imagem docker"
docker build -t cctransproc:$snapshot_version .

echo "[STEP 03] iniciando o ambiente"
docker compose -f ./docker/docker-compose.yml up -d

echo "Ambiente de teste criado com sucesso!"