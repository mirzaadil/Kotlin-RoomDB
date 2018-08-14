# Model-View-Presenter: Android Architecture. 

# Libraries Used

- Retrofit - For the network calls.
- RxJava - For making Async Retrofit calls at the same time their responses.
- Glide  - Glide is a powerful system for displaying images in Android applications..
- Unit Testing.

# Project Structure

- mirzaadil.nytimes.view.activities -Contains all the activities.
- mirzaadil.nytimes.view.adapters -Contains all the adapter. In this project only the Recycler Adapters.
- mirzaadil.nytimes.model - Contains the model classes of the entities in the project.
- mirzaadil.nytimes.controllers.network - Contains everything about network implementation.
- mirzaadil.nytimes.controllers.utils - Contains all helpers supporting code.

# Model-View-Presenter: Android guidelines

MVP architecture and there are a lot of different implementations. There is a constant effort by the developer community to adapt this pattern to Android in the best way possible.

If you decide to adopt this pattern, you are making an architectural choice and you must know that your codebase will change, as well as your way to approach new features (for the better). You must also know that you need to face with common Android problems like the Activity lifecycle and you may ask yourself questions like:

# should I save the state of the presenter?
# should I persist the presenter?
# should presenter have a lifecycle?
# In this article, I’m going to put down a list of guidelines or best practices to follow in order to:

solve the most common problems (or at least those ones I’ve had in my personal experience) using this pattern
maximize the benefits of this pattern
First of all, let’s describe the players:

# Model: 
it is an interface responsible for managing data. Model’s responsibilities include using APIs, caching data, managing databases and so on. The model can also be an interface that communicates with other modules in charge of these responsibilities. For example, if you are using the Repository pattern the model could be a Repository. If you are using the Clean architecture, instead, the Model could be an Interactor.
# Presenter: 
the presenter is the middle-man between model and view. All your presentation logic belongs to it. The presenter is responsible for querying the model and updating the view, reacting to user interactions updating the model.
# View: 
it is only responsible for presenting data in a way decided by the presenter. The view can be implemented by Activities, Fragments, any Android widget or anything that can do operations like showing a ProgressBar, updating a TextView, populating a RecyclerView and so on.

# Authors
- Mirza Adil
