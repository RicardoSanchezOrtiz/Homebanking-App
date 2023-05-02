const {createApp} = Vue

createApp({
    data(){
        return{
            client: undefined,
            loans: undefined,
            accBalance: undefined,
            error:undefined,
            accounts: undefined,
            acctype: undefined,
            DeleteAccNumber:null,
            receivingAcc: null,
            USDollar: new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
            })
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
                this.loans = response.data.clientLoans
                this.accounts = response.data.accounts.filter(a => a.active == true )
            })
        },
        logOut(){
            axios.post('/api/logout')
            .then(() => window.location.href="../index.html")
        },
        createAccount(){
            axios.post("/api/clients/current/accounts", `email=${this.client.email}&type=${this.acctype}`, {headers:{'Content-Type':'application/x-www-form-urlencoded'}})
            .then(() => {
                window.location.reload
                this.loadData()
            }).catch(() =>{
                this.error = error.response.data.message
                console.log(this.error)
            })
        },
        deleteAccount(){
            axios.patch("/api/clients/current/accounts", `number=${this.DeleteAccNumber}&accNumber=${this.receivingAcc}`)
            .then(() => {
                console.log("success")
                window.location.href="./accounts.html"
            })
        },
}
}).mount("#app")
