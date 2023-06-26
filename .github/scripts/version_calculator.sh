#!/bin/bash

calculate_next_version() {
  local latest_tag
  local commits
  local major
  local minor
  local patch
  local bug

  # Extract latest tag
  latest_tag=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.0.0")

  # Determine commit messages since last tag
  commits=$(git log --oneline "${latest_tag}"..HEAD)

  # Default version components
  major=$(cut -d. -f1 <<< "$latest_tag")
  minor=$(cut -d. -f2 <<< "$latest_tag")
  patch=$(cut -d. -f3 <<< "$latest_tag")

  # Check commit messages and increment version components
  if [[ $commits == *"BREAKING CHANGE"* ]]; then
    echo "$((major + 1)).0.0"
  elif [[ $commits == *"Add"* ]]; then
    echo "${major}.$((minor + 1)).0"
  else
    echo "${major}.${minor}.$((patch + 1))"
  fi
}
