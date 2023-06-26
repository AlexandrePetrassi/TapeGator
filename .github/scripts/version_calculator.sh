#!/bin/bash

calculate_next_version() {
  local latest_tag
  local commits
  local major
  local minor
  local patch

  # Extract latest tag
  latest_tag=$(git describe --tags --abbrev=0 2>/dev/null || echo "0.0.0")

  # Determine commit messages since last tag
  commits=$(git log --oneline "${latest_tag}"..HEAD)

  # Default version components
  major=$(cut -d. -f1 <<< "$latest_tag")
  minor=$(cut -d. -f2 <<< "$latest_tag")
  patch=$(cut -d. -f3 <<< "$latest_tag")

  # Flag to track version increment
  incremented=false

  # Check commit messages and increment version components
  while IFS= read -r commit; do
    if [[ $commit == "BREAKING CHANGE"* ]]; then
      major=$((major + 1))
      minor=0
      patch=0
      incremented=true
      break  # Break out of the loop
    elif [[ $commit == "Add"* ]]; then
      minor=$((minor + 1))
      patch=0
      incremented=true
      break  # Break out of the loop
    fi
  done <<< "$commits"

  # Increment patch if no major or minor increment occurred
  if ! $incremented; then
    patch=$((patch + 1))
  fi

  echo "${major}.${minor}.${patch}"
}
