# Contributing to `OneMed1a`

Thank you for your interest in contributing to `OneMed1a`.
This guide will help you understand how to get involved, submit your work, and collaborate effectively.

> Please also read our [README.md](README.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before contributing.

## Filing a Bug Report
If you find a bug:
1. **Search** existing [issues](https://github.com/SOFTENG-310-OneMed1a/OneMed1a_App/issues) to avoid duplicates.
2. If it’s new, **open a Bug issue** following the [bug report template](.github/ISSUE_TEMPLATE/bug_report.md).

## Suggesting a New Feature
1. **Check** existing [feature requests](https://github.com/SOFTENG-310-OneMed1a/OneMed1a_App/issues?q=is%3Aissue%20state%3Aopen%20type%3AFeature).
2. If not found, open a **Feature Request issue** following the [feature request template](.github/ISSUE_TEMPLATE/feature_request.md).

## Submitting a Pull Request
1. **Check** existing [pull requests](https://github.com/SOFTENG-310-OneMed1a/OneMed1a_App/pulls) to avoid duplicates.
2. If not found:
   - Make your changes in a new git branch:

      ```shell
      git checkout -b my-fix-branch main
      ```
    - Make sure to test your code and ensure it works and nothing else breaks.
   
    - Commit your changes with a descriptive commit message:
  
      ```shell
      git commit -am "commit message"
      ```

    - Push your changes to GitHub:

      ```shell
      git push origin my-fix-branch
      ```

    - In GitHub, send a pull request to `OneMed1a:main`.

## Environment set up and running tests
**TO BE COMPLETED**
  
## Contributions we welcome
### We are looking for:
  - Bug fixes
  - New features aligned with our project goals
  - Documentation
  - Tests
  - Refactoring for readability and maintainability
  - Design and UX improvements
### We aren't looking for:
  - Features outside of our project scope
  - Large architectural changes without an RFC/discussion
  - Anything violating our [Code of Conduct](CODE_OF_CONDUCT.md)

## How newcomers can get started
Newcomers can get started by heading over to [issues](https://github.com/SOFTENG-310-OneMed1a/OneMed1a_App/issues?q=is%3Aissue%20state%3Aopen%20label%3A%22good%20first%20issue%22)
and start working on any issues labeled with "good first issue".

## Technical requirements for contributors
### Frontend:
  - Next JS

### Backend:
  - Java SpringBoot
  - Supabase
  - postgreSQL

## Project vision & roadmap
`OneMed1a` aims to develop a unified web application that aggregates movies, TV shows, books,
and podcasts into a single discovery hub. By ingesting each user’s ratings, reviews and
wish-lists, alongside their friends’ activity, the system will generate context-aware, cross-media
recommendations (e.g. “you just finished that novel; here’s its film adaptation or a related
podcast”). A consolidated activity feed will let users track, share and save everything in one
place, transforming fragmented media browsing into a cohesive, socially driven experience.

### Our current roadmap includes:
1. Area to track media you have watched
   - Movies
   - TV Shows
   - Music
   - Books / Ebooks
   - Podcasts
2. Recommendation features:
   - Cross-media recommendation features using user’s taste from their data.
3. Tracking of watched/watching media
   - Completed, watching, plan to watch
4. Creating accounts
   - Allows users to save their accounts and save their data
5. Friends feature:
   - Add friends
   - View friend’s rating
   - View friend’s analytics (number of movies/media watched)
   - Recommends to you the friend’s top picks
6. Recommendation Features
   - Modify recommendation implementation to consider mood
7. Blend Mode (if time allows)
   - Choose a friend, combine and compare data and return a compatibility score
   - Scoreboard to display compatibility scores

## High-level design
### Frontend
  - Next JS
### Backend
  - Java SpringBoot
### Database
  - postgreSQL
### User authentication
  - Supabase
### APIs
  - OpenAI
  - ??? more when we find out

## Project ground rules
To ensure consistency throughout the source code, keep the following rules while you are working:
  - All features or bug fixes **Must be tested** by one or more tests.
  - All classes and methods **Must be documented**.
  - Follow the [code of conduct](CODE_OF_CONDUCT.md).

## How contributors should get in touch
Join the discord: https://discord.gg/rsDuRvQuPN
    
