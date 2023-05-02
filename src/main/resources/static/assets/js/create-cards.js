const {createApp} = Vue

createApp({
    data(){
        return{
        client: undefined,
        cardType: undefined,
        cardColor: undefined,
        error: undefined
        }
    },
    created(){
        this.loadData()
    },

    methods: {
        loadData(){
            axios.get("http://localhost:8080/api/clients/current")
            .then(response =>{
                this.client = response.data
            })
        },
        createCard(e){
            e.preventDefault()
            axios.post("http://localhost:8080/api/clients/current/cards", `type=${this.cardType}&color=${this.cardColor}`
            ,{headers:{'Content-Type':'application/x-www-form-urlencoded'}})
            .then(() => {
                window.location.href = '../web/cards.html'})
            .catch(error => {
                this.error = error.response.data
                console.log(this.error)
                Swal.fire({
                    title: `${this.error}`,
                    showClass: {
                        popup: 'animate__animated animate__fadeInDown'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__fadeOutUp'
                    }
                    })
                })
        },
        logOut(){
            axios.post('/api/logout')
            .then(() => window.location.href="../index.html")
        }
    }
}).mount("#app")