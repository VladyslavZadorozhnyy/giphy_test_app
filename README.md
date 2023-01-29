## This repository is an implementation of a test task application for a position of Android developerin one of the companies.

## Product Requirements description (Screen 1):
- Displaying gif images as a list/table
- Image uploads must be page-by-page
- Image search is done via keyboard input
- Offline mode is expected: caching of images in internal storage and links to the files on the local database
- The ability to remove an image from local storage, while such an image should not be displayed in the list on the screen during subsequent requests to the server

## Product Requirements description (Screen 2):
- Full screen display of the selected gif
- Ability to view other gifs using a horizontal swipe on the screen

## Requirements:
- Screen rotation should be considered
- MVVM pattern to be used
- Dependency injection to be used

## Other technologies and approaches, used in the implementation:
- Kotlin coroutines
- Clean Architecture
- Giphy API
- Glide library
- Room library
