const{createApp} = Vue

createApp({
    data(){
        return{
            client: undefined,
            accounts: undefined,
            sendingAcc: undefined,
            recieveAcc: undefined,
            amount: 0,
            description: undefined,
            loans: undefined,
            error:undefined,
            directed: 1
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
                this.accounts = response.data.accounts.filter(a => a.active == true )
            })
        },
        transaction(e){
            e.preventDefault()
            axios.post("http://localhost:8080/api/clients/current/accounts/transactions"
            ,`transactionAmount=${this.amount}&description=${this.description}&transferingAccNum=${this.sendingAcc}&recieverAccNum=${this.recieveAcc}`
            ,{headers:{'Content-Type':'application/x-www-form-urlencoded'}})
            .then(() => {
                window.location.href = '../web/accounts.html'
                console.log("success")
            }).catch(error => {
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