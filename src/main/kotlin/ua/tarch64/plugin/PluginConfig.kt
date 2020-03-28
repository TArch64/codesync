package ua.tarch64.plugin

class PluginConfig {
    var gatewayServiceUrl: String = "https://tarch64.ngrok.io"
    val username: String = System.getenv("USER")
    var roomId: String = ""

    fun isConnectedToRoom() = this.roomId.isNotEmpty()
}