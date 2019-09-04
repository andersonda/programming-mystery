import tornadofx.*
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.system.exitProcess

class Application: App(ApplicationView::class)

class ApplicationView: View("Programming Mystery") {
    private val questionView: QuestionView by inject()
    val io: IOPanel by inject()

    override val root = borderpane {
        top =  menubar {
            menu("File") {
                menu("Load"){
                    item("Code").setOnAction {
                        loadQuestion()
                    }
                    item("Teams").setOnAction {
                        loadTeams()
                    }
                }
                item("Quit").setOnAction {
                    exitProcess(0)
                }
            }
            menu("View"){
                menu("Zoom"){
                    item("In").setOnAction {
                        questionView.resize(1)
                    }
                    item("Out").setOnAction {
                        questionView.resize(-1)
                    }
                    item("Reset").setOnAction {
                        questionView.resize(0)
                    }
                }
            }
        }
        center = hbox()
    }

    init {
        loadQuestion()
        root.children[1] += questionView.root
        root.children[1] += io.root
        currentStage?.isMaximized = true;
    }

    fun loadQuestion(){
        questionView.chooseQuestion()
        questionView.loadAnswers()
    }

    fun loadTeams(){
        val chooser = JFileChooser()
        chooser.dialogTitle = "Open Team Names"
        chooser.fileFilter = FileNameExtensionFilter("TXT files", "txt")
        chooser.currentDirectory = File("./src/main/resources")
        if(chooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
            val teams = chooser.selectedFile.readText().lines()
            io.teams.populateTeams(teams)
            io.scores.populateScores()
        }
    }
}