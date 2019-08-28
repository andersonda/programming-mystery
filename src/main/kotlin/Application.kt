import tornadofx.*

class Application: App(ApplicationView::class)

class ApplicationView: View("Programming Mystery") {
    private val questionView: QuestionView by inject()
    val io: IOPanel by inject()
    val progressBar = progressbar()

    override val root = stackpane()

    init {
        root += progressBar
        loadQuestion()
        root += borderpane {
            left = questionView.root
            right = io.root
        }
    }

    fun loadQuestion(){
        progressBar.isVisible = true
        questionView.chooseQuestion()
        questionView.loadAnswers()
        progressBar.isVisible = false
    }
}