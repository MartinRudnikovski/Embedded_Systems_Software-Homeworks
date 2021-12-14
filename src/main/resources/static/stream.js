let USSLabelElement
let ServoLabelElement

class Stream{
    constructor(endpoint) {
        this.endpoint = endpoint
        this.eventSource = null
    }
    init(handleEvent) {
        this.eventSource = new EventSource(this.endpoint)
        this.eventSource.addEventListener("message", handleEvent)
        this.eventSource.onerror = () =>{
            console.log("Error occurred")
            this.close()
        }
    }

    close() {
        this.eventSource.close()
    }
}

const handleEventUSS = (event) => {
    const val = JSON.parse(event.data)
    if (val <= 20 && val > -1){
        USSLabelElement.innerText = "Someone has entered the room!"
        USSStream.close()
    }

}

const handleEventServo = (event) => {
    ServoLabelElement.innerText = JSON.parse(event.data)
}

const USSStream = new Stream("/ussStream")
const ServoStream = new Stream("/servoStream")

window.onload = () => {
    USSStream.init(handleEventUSS)
    ServoStream.init(handleEventServo)
    USSLabelElement = document.getElementById("USSLabel")
    ServoLabelElement = document.getElementById("servoLabel")
}

window.onbeforeunload = () => {
    USSStream.close()
    ServoStream.close()
}