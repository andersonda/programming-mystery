import tornadofx.*

class Application: App(ApplicationView::class){
    init {

    }
}

class ApplicationView: View("Programming Mystery") {
    private val questionView: QuestionView by inject()
    private val io = IOPanel()

    override val root = borderpane {
        left = questionView.root
        right = io.root
    }
}