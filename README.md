1.To have a globally consistent thing -> eg dimension
	:Create dimension resource file and define the values in there

2. Creating a ripple

3. View binding and delete binding

4. Progress bars

5. Implementing count down timers and destroy it

6. Implement list of objects :
	a.Create a exercise model class
	b.Create a constant object that adds the objects to arrayList nd returns it -> Creating in constant allows us to globally access in the project
	c.Use the list returned in activity to perform action

7. Implementing TTS and destroy it

8. How to play sound inside app and destroy it

9. Implementing Recycler View:
	a.Creating data class
	b.Creating data objects
	c.Creating an item design
	d.Creating the adapter
	f.Linking the adapter to recycler in activity
	g.OnBind, OnCount etc
	h.NotifyDatasetChange
	
10. Create radio group and set colors according to checked state

11.Companion Object

12. Radio group onSetCheckedListener

13. Room Database:</br>
	a. Entity DAO and Database</br>

	b. app gradle: </br>
		Plugin id("kotlin-kapt")
		
		Dependancies:
		//room and lifecycle dependencies
    		implementation("androidx.room:room-runtime:2.6.1")
   		 kapt("androidx.room:room-compiler:2.6.1")
   		 //coroutine support
   		 implementation("androidx.room:room-ktx: 2.3.0")
   		 implementation("androidx.activity:activity-ktx:1.9.0")

	
 	</br>c. Entity: Create a data class</br>
	d. Create a DAO (interface)</br>
	e. Create a database</br>
	f. Create application to initialise database</br>
	g. Update manifest</br>
	h. Create function to add record</br>
	i. Pass DAO into record adding function</br>
	j. Create recycler view adapter (special care for update and delete buttons)</br>
	k. Populate the recycler view from database</br>
	l. update and delete buttons are functional</br>

