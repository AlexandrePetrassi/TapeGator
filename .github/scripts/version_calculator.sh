#!/bin/bash

calculate_next_version() {
  local latest_tag
  latest_tag=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.0.0")

  local major
  local minor
  local patch

  major=$(cut -d. -f1 <<< "$latest_tag")
  minor=$(cut -d. -f2 <<< "$latest_tag")
  patch=$(cut -d. -f3 <<< "$latest_tag")

  if [[ "$minor" == "" ]]; then
    minor=0
  fi

  if [[ "$patch" == "" ]]; then
    patch=0
  fi

  patch=$((patch + 1))

  echo "${major}.${minor}.${patch}"
}
