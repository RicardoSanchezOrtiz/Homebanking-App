const {createApp} = Vue

createApp({
    data(){
        return{
            data: {},
            newClient: {firstName:"" , lastName:"" , email: "" },
            clientArray: []
            }
    },
    created(){
        this.loadData()
    },
    methods: {
        loadData(){
            axios.get("http://localhost:8080/api/clients")
            .then(response =>{
                this.data = response
                console.log(this.data.data)
                this.clientArray = this.data.data
            }
            )
        },
        addClient(){
            axios.post("http://localhost:8080/api/clients", this.newClient)
            .then(response => {
                this.data = response
                this.loadData()
            })
        },
        deleteClient(client){
            axios.delete(client._links.client.href)
            .then(response => this.loadData())
        }
    }

}).mount("#app")