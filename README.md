# Goal
Build an Android app for people to memorize dog breeds.

# Bare minimum feature
- show a picture of a dog, ask the user which breed it is
- allow the user to answer
- show result

# Resources
Engineer x 1, 3 hours

Public API
- Get a list of all breeds
- Get a random dog picture
- Get a picture of a certain breed
    - All images of that breed
    - One random image
    - Multiple random images
- Get all the sub-breeds from a breed
    - All images of that breed
    - One random image
    - Multiple random images

# Architecture design
------------------------- data ------------------------->

Data layer         Domain layer        UI layer

<------------------------ event ------------------------- 

# Task breakdown
| Category | Details                                |
|----------|----------------------------------------|
| Set up   | Basic set up for a single page app     |
| UI       | Show a static image                    |
|          | Show the question text                 |
|          | Give three options for breeds          |
|          | Support clicking on the options        |
|          | Load remote image                      |
|          | Display correct/wrong result           |
| Data     | Set up network request framework       |
|          | Fetch list of all breeds               |
|          | Get a random breed and fetch a picture |
|          | Unit tests                             | 
| Add-on   | Polish UI                              |
|          | Refresh image & options                |
|          | Edge cases                             |

# Feature extensions

# Optimisations

