rootProject.name = "FlickrExample"
include(
    ":kwon-dae-won:app",
    ":kwon-dae-won:data",
    ":kwon-dae-won:domain",

    ":hello-compose-app:shared:domain",
    ":hello-compose-app:shared:remote",
    ":hello-compose-app:shared:local",
    ":hello-compose-app:shared:data",
    ":hello-compose-app:shared:core-mvi",
    ":hello-compose-app:presentation:main",
    ":hello-compose-app:presentation:detail",
    ":hello-compose-app:ui:system",
    ":hello-compose-app:ui:image",

    ":hello-compose-app",
    ":good-bye-xml-app",
    ":sum3years-app",
)