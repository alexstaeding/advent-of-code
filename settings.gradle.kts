dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "advent-of-code"

val years = file("years")

sequenceOf(
    "2022",
    "2023",
    "2024",
).forEach {
    val project = ":advent-of-code-$it"
    include(project)
    project(project).projectDir = years.resolve(it)
}
