
# Final Year Project Diary


## Week 1

- I met with my supervisor to discuss what the project entails and to also discuss my initial plan. 
- I am struggling to decide on what product I want to build but I am thinking about creating a store application that allows for individual users, teams and enterprise users.
- I have been researching technologies that I am planning on using both now and In the future.
- After researching the technologies I wanted to use I found some issues with how to test them so I am evaulating what would be the best option, I also asked my supervisor what they thought would be a good approach, with the advice I got and the research I completed I will be able to narrow down what to use.
- Finally I have been working on my project plan and have started the abstract, created an intial timeline and started thinking about the risks my project has.

## Week 2

- I continued working on my project plan, I had some areas where I was unsure so I asked my supervisor to read over one of my drafts. 
- I continued to research web technologies and I am planning on setting up the initial application.
- I updated my plan based on the recomendations I recieved and finished it off. 
- I submitted the project plan and merged it into the main branch of this repo as well as creating a tag for it so that I can easily track when it was completed. 
- I had planned on setting up the intial application and I will have some of it set up but it will be hard to fully understand the requirments until my project plan has been marked and I have some feedback on it.

## Week 3

- I spent the majority of this week planning and researching.
- I have decided to use a Java Spring boot application with a PostgreSQL database for the backend and a Typescript Next.js Frontend.
- I created an initial setup for both the frontend and backend and I plan to continue working on the setup over the next week.
- I did lots of research on all of these frameworks as well as authentication providers and payment providers. 

## Week 4 

- I setup the authentication provider that I am planning to use. To do this I used the research I did on AWS Cognito and Terraform so that I could provision it using Infrastructure as Code. 
- I also setup apollo client on the frontend which I will use to manage the states of my data and interact with the backend of the application. 
- Finally I setup graphql codegen which will enable me to generate type safe code from my graphql schema. 


## Week 5 

- I setup authentication on the frontend which now allows for a user to login if they have a cognito user account. 
- I then setup the use of JWT for authorisation. This was only setup to send the JWT to the backend. 
- I started adding JWT verification on the backend but I started getting lots of issues with CORS once I added spring security. This meant that the issue is rolling over into Week 6.

## Week 6 

- I met with my supervisor this week and discussed my current progress, I identified that I need to start my reports as soon as possible so I am planning on doing that. 
- I finished off the intital backend securtity that I was working on and merged it.
- I have been building the user interface out.
- Created different security profiles for the backend.
- I created a database and an initial users table in it.

## Week 7

- Created unit and integration tests for the backend.
- Added checkstyle to the backend and fixed existing issues.
- Started to create gitlab ci pipelines for the project.
- Started my interim report.

## Week 8

- Continued to work on the pipelines but had lots of issues with the images they would run on.
- Added a userDetails table which contained basic information about a user.
- Created the ui for the user details.
- Researched Netflix dgs to fix errors and created custom datafetchers for the user details.


## Week 9

- Finalised the user details ui, backend and the database schema and functions.
- I updated the security config, specifically the jwt decoder.
- Fixed the backend pipeline as the new changes were causing errors.
- Continued to work on my interim report.

## Week 10

- I added items to the database and backend.
- I setup intial searching of the items.
- Created and completed the presentation slides for week 11.
- I added end to end and component testing to the frontend.
- Added and configured knip for the frontend.

## Week 11

- Updated the frontend security info.
- Setup initial stripe subscriptions.
- Added steps to setup the stripe subscriptions.
- Presented my presentation and watched peer presentations.
- Finished the interim report.

## Week 12
- Assessed status of project and made a plan on what to do going forward.
- Created SQL tests for the database.
- Setup Redis as a cache.
- Setup Cache connection from backend.
- Implemented initial caching with Redis.
- Setup Cognito in the integration tests.

## Week 13 
- Setup a delete user functionality that soft deletes users so that some important information is not lost.
- Started to work on the AWS infrastructure.
- Changed the setup of the IAC.

## Week 14
- There was lots of progress with the infrastructure this week but it was all works in progress so there is not much in the diary.

## Week 15
- The infastructure overhall was completed this week but there are still things to work on with it.
- A production merge was made after configuring the code for it.
- The User interface for searching was created and the implementation was started.

## Week 16
- There was more infrastructure overhall this week as the load balancers were setup and ssl.
- Work on SES and the implementation was started here and hoping to be completed by the end of the week.

## Week 17
- The SES infrastructure and implementation were completed like planned.
- An integration with Jira for bug reporting was created.
- A backend logging solution was added.
- Fixes for the integration tests were completed.

## Week 18
- Started the final report.
- Lots of new backend features were created this week.
- Queries and mutations for items were made.
- Pagnitation was added and worked on.
- The create item ui was made.

## Week 19
- Queries to get items by users were made.
- The backend was restructured and refactored.
- The graphql schema was restructured as well.
- Pages for the users items were created.
- A production merge was made.

## Week 20
- Frontend pagination, sorting and filtering were added where possible.
- The shops items got an overhall.
- Admin features were started to be worked on.

## Week 21
- Admin features were create and worked on heavily.
- There were lots of fixes to do with the admins.
- Especially to do with how the cahcing worked here.
- Production merge.

## Week 22
- More infrastructure updates.
- This mainly was to do with the CICD.
- Test, build and deployment stages were created inside a pipeline.

## Week 23
- An integration with OpenAi was started.
- A AI chatbot was added.
- Work for an apollo gateway was started.

## Week 24
- There were lots of updates to the gateway and it was completed.
- But it was parked for now as the backend is still one service
- The infrastruture was also setup and parked.

## Week 25
- The bidding service was setup and completed.
- Kafka was configured and added.
- Final infrastructure updates were made. 
- The final changes were added. 
- The final report was finsihed.