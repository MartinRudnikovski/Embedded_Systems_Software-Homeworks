let labelElement

class Stream{
    constructor(endpoint) {
        this.endpoint = endpoint
        this.eventSource = null
    }
    init() {
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

const handleEvent = (event) => {
    const val = JSON.parse(event.data)
    console.log(val)
    if (val <= 20 && val > -1)
        labelElement.innerText = "Someone has entered the room!"
}


const stream = new Stream("/ussStream")

window.onload = () => {
    stream.init()
     labelElement = document.getElementById("label")
}

window.onbeforeunload = () => {
    stream.close()
}