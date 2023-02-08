rootProject.name = "FlickrExample"
include(
    ":kwon-dae-won-app",
    ":hello-compose-app",
    ":good-bye-xml-app",
    ":sum3years-app",
)
include(":hello-compose-app:shared:domain")
include(":hello-compose-app:shared:remote")
include(":hello-compose-app:shared:data")
include(":hello-compose-app:presentation:main")
include(":hello-compose-app:ui:system")
