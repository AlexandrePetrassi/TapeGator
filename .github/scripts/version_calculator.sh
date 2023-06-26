#!/bin/bash

calculate_next_version() {
  local latest_tag
  local commits
  local major
  local minor
  local patch

  latest_tag=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.0.0")
  commits=$(git log --pretty=%s "$latest_tag"..HEAD)
  major=$(cut -d. -f1 <<< "$latest_tag")
  minor=$(cut -d. -f2 <<< "$latest_tag")
  patch=$(cut -d. -f3 <<< "$latest_tag")

  for commit in $commits; do
    if [[ $commit == "BREAKING CHANGE"* ]]; then
      major=$((major + 1))
      minor=0
      patch=0
    elif [[ $commit == "Add"* ]]; then
      minor=$((minor + 1))
      patch=0
    else
      patch=$((patch + 1))
    fi
  done

  echo "${major}.${minor}.${patch}"
}
