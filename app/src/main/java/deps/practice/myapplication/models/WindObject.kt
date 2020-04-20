package deps.practice.myapplication.models

class WindObject {

    var speed: Float = 0.0f
    var deg: Float = 0.0f

    init {
        println(this.toString())
    }

    override fun toString() = "Speed ${speed}, deg ${deg}"

}