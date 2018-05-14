# Assignment
# Libraries Used
- Retrofit - For the network calls.
- RxJava - For making 2 Async Retrofit calls at the same time and combining their responses.
- Picasso - For rendering images.
- RoomDB - For Save Data.
- Lottile - For Animation.
- Unit Testing.

# Project Structure
- com.mirzaadil.assignmentpayconiq.activities -Contains all the activities.
- com.mirzaadil.assignmentpayconiq.adapters -Contains all the adapter. In this project only the Recycler Adapters.
- com.mirzaadil.assignmentpayconiq.database.db - Contains contains all DataBase Classes.
- com.mirzaadil.assignmentpayconiq.database.db - Contains contains all DataBase Classes.
- com.mirzaadil.assignmentpayconiq.models - Contains the model classes of the entities in the project.
- com.mirzaadil.assignmentpayconiq.networks - Contains everything about network implementation.
- com.mirzaadil.assignmentpayconiq.utils - Contains all helpers supporting code.

# MVP

The MPV pattren is used to develop this application. In each activity package you can find a presentaion layer for that particular activity. All of the application logic and functionality about any particular activity is implemented in it's respective presenter. So, essentially I tried to seperate my application logic from my views. If there is any change in future or some additional feature needs to be added then we shall be sure about where exactly the change is going to happen in the code.

Using MVP pattren makes this application highly extensible and maintainable.
Using Kotlin in Database (RoomDM).

# Authors
- Mirza Adil
