import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*

class TeamView : View() {

    val question: QuestionView by inject()
    val io: IOPanel by inject()

    var teams = listOf<Team>()
        private set

    override val root = gridpane{
        hgap = 8.0
        alignment = Pos.CENTER
        columnConstraints.addAll(
            ColumnConstraints(),
            ColumnConstraints().apply { hgrow = Priority.ALWAYS })
    }

    init {
        populateTeams(listOf("Team 1", "Team 2", "Team 3" ,"Team 4"))
    }

    fun populateTeams(newTeams:List<String>){
        root.removeAllRows()
        teams = newTeams.map { Team(it, 0) }

        teams.forEachIndexed {index, team ->
            val font = Font(18.0)
            val image = ImageView("question-mark.png")
            image.fitWidth = 18.0
            image.fitHeight = 18.0

            root.add(Text(team.name).apply { this.font = font }, 0, index)
            root.add(TextField().apply{ this.font = font }, 1, index)
            root.add(image, 2, index)
        }
    }

    fun checkAnswers(){
        saveResponses()
        val responses = question.responses!![io.outputLine]
        when(responses.numCorrect){
            0 -> io.makeMascotSad()
            teams.size -> io.makeMascotHappy()
            else -> io.makeMascotNeutral()
        }
        responses.correctIndices.forEachIndexed { index, isCorrect ->
            (root.children[3 * index + 2] as ImageView).image = when(isCorrect){
                true -> {
                    teams[index].score += 1
                    Image("checkmark.png")
                }
                false -> Image("x-mark.png")
            }
        }
        io.navigation.disableCheck()
        disableInput()
        io.scores.populateScores()
    }

    fun saveResponses(){
        val responses = question.responses!![io.outputLine]
        responses.clearResponses()

        teams.forEachIndexed { index, _ ->
            responses.setResponse((root.children[3 * index + 1] as TextField).text, index)
        }
    }

    fun loadResponses() {
        val responses = question.responses!![io.outputLine]

        if(responses.checked) {
            responses.getResponses().forEachIndexed { index, response ->
                val textFieldIndex = 3 * index + 1
                val node = root.children[textFieldIndex] as TextField
                node.text = response
                (root.children[textFieldIndex + 1] as ImageView).image = when (node.text) {
                    question.answers!![io.outputLine] -> {
                        Image("checkmark.png")
                    }
                    else -> Image("x-mark.png")
                }
            }
            io.navigation.disableCheck()
            disableInput()
            when(responses.numCorrect){
                0 -> io.makeMascotSad()
                teams.size -> io.makeMascotHappy()
                else -> io.makeMascotNeutral()
            }
        }
        else{
            responses.getResponses().forEachIndexed { index, response ->
                val textFieldIndex = 3 * index + 1
                val node = root.children[textFieldIndex] as TextField
                node.text = response
                (root.children[textFieldIndex + 1] as ImageView).image = Image("question-mark.png")
            }
            io.makeMascotNeutral()
            io.navigation.enableCheck()
            enableInput()
        }
    }

    fun disableInput() = teams.forEachIndexed { index, _ ->
        (root.children[3 * index + 1] as TextField).isDisable = true
    }

    fun enableInput() = teams.forEachIndexed { index, _ ->
        (root.children[3 * index + 1] as TextField).isDisable = false
    }
}
