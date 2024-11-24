# Goal
Build an Android app for people to memorize dog breeds.

# Bare minimum feature
- show a picture of a dog, ask the user which breed it is
- allow the user to answer
- show result

# Resources
Engineer x 1, 3 hours

Public API
- <mark>Get a list of all breeds</mark>
- Get a random dog picture
- Get a picture of a certain breed
    - All images of that breed
    - <mark>One random image</mark>
    - Multiple random images
- Get all the sub-breeds from a breed
    - All images of that breed
    - One random image
    - Multiple random images

# Architecture design
![arch-diagram.png](media%2Farch-diagram.png)

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

# Screen recording
![Screen_recording_20241124_155922.gif](media%2FScreen_recording_20241124_155922.gif)

# Product feature extensions
| Priority | Est. effort | Feature                                                |
|----------|-------------|--------------------------------------------------------|
| P0       | S           | Update app icon                                        |
| P1       | M           | Add loading & error state for image                    |
| P2       | M           | Show the number of consecutive correct answers (combo) |
| P2       | S           | Support saving favorite breeds                         |
| P3       | L           | Support browsing all breeds with images                |
| P4       | XL          | Play online and compete with friends                   |

# Technical feature extensions
- Upload exception logs
- Analytics logs: performance, user behaviour
- Cache data in local database (to support faster data loading and reduce server QPS)
- Dependency injection