class Responses(private val correct: String, private val count: Int) {
    private var responses = MutableList(count){ "" }
    var checked = false
        private set
    val correctIndices by lazy {
        checked = true
        responses.map {
            when(it){
                correct -> true
                else -> false
            }
        }
    }
    val numCorrect by lazy {
        correctIndices.count { it }
    }

    fun getResponses() = responses.toList()
    fun setResponse(response: String, index: Int){ responses[index] = response }
    fun clearResponses(){ responses = MutableList(count){ "" } }
}