package com.example.workoutapp

class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var completed: Boolean,
    private var isSelected: Boolean
) {
    // Getter and Setter for id
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    // Getter and Setter for name
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    // Getter and Setter for image
    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    // Getter and Setter for completed
    fun getCompleted(): Boolean {
        return completed
    }

    fun setCompleted(completed: Boolean) {
        this.completed = completed
    }

    // Getter and Setter for isSelected
    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}
