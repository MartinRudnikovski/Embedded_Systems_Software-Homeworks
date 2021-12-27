let USSLabelElement
let ServoLabelElement
let InfLabelElement
let AirHumidityLabelElement
let AirTemperatureLabelElement
let SoilMoistureLabelElement

class Stream{
    constructor(endpoint) {
        this.endpoint = endpoint
        this.eventSource = null
    }
    init(handleEvent) {
        this.eventSource = new EventSource(this.endpoint)
        this.eventSource.addEventListener("message", handleEvent)
        this.eventSource.addEventListener("data", handleEvent)
        this.eventSource.onerror = () =>{
            console.log("Error occurred")
            this.close()
        }
    }

    close() {
        this.eventSource.close()
    }
}

const handleSoilMoisture = (event) => {
  SoilMoistureLabelElement.innerText = "Soil moisture = " + JSON.parse(event.data) + "%"
}

const handleAirHumidity = (event) => {
    AirHumidityLabelElement.innerText = "Humidity = " + JSON.parse(event.data) + "%"
}

const handleAirTemperature = (event) => {
    AirTemperatureLabelElement.innerText = "Temperature = " + JSON.parse(event.data) + "C"
}

const handleEventUSS = (event) => {
    const val = JSON.parse(event.data)
    if (val <= 20 && val > -1)
        USSLabelElement.innerText = "Someone has entered the room!"

}

const handleEventServo = (event) => {
    ServoLabelElement.innerText = JSON.parse(event.data)
}

const handleInf = (event) =>{
    if (JSON.parse(event.data) > 0){
        InfLabelElement.innerText = "Someone has passed the door.";
        document.getElementById("img").setAttribute("src", "http://192.168.100.24")
        document.getElementById("img_bttn").setAttribute("value", "Close live stream.")
    }
    else{
        InfLabelElement.innerText = "";
        document.getElementById("img").setAttribute("src", "")
        document.getElementById("img_bttn").setAttribute("value", "Start live stream.")
    }
}

const USSStream = new Stream("/ussStream")
const ServoStream = new Stream("/servoStream")
const InfStream = new Stream("/inf")
const AirHumidityStream = new Stream("/airHumidity")
const AirTemperatureStream = new Stream("/airTemperature")
const SoilMoistureStream = new Stream("soilMoisture")

window.onload = () => {
    USSStream.init(handleEventUSS)
    ServoStream.init(handleEventServo)
    InfStream.init(handleInf)
    AirHumidityStream.init(handleAirHumidity)
    AirTemperatureStream.init(handleAirTemperature)
    SoilMoistureStream.init(handleSoilMoisture)


    USSLabelElement = document.getElementById("USSLabel")
    ServoLabelElement = document.getElementById("servoLabel")
    InfLabelElement = document.getElementById("infLabel")
    AirHumidityLabelElement = document.getElementById("airHumidityLabel")
    AirTemperatureLabelElement = document.getElementById("airTemperatureLabel")
    SoilMoistureLabelElement = document.getElementById("soilMoistureLabel")
}

window.onbeforeunload = () => {
    USSStream.close()
    ServoStream.close()
    InfStream.close()
    AirHumidityStream.close()
    AirTemperatureStream.close()
    SoilMoistureLabelElement.close()
}


function enableVideo(){
    let img = document.getElementById("img")
    let bttn = document.getElementById("img_bttn")
    if (img.getAttribute("src").length === 0){
        img.setAttribute("src", "http://192.168.100.24")
        bttn.setAttribute("value", "Close live stream.")
    }
    else {
        img.setAttribute("src", "")
        bttn.setAttribute("value", "Start live stream.")
    }
}

