const {createApp} = Vue

createApp({
    data(){
        return{
            client:undefined,
            data: {},
            account:{},
            transactions: {},
            id: undefined,
            amountDollars:null,
            USDollar: new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
            })
        }
    },
    created(){
        
        this.loadClientData()
        this.paramReading()
        this.loadData()
    },
    methods: {
        
        
        loadClientData(){
            axios.get("http://localhost:8080/api/clients/current")
            .then(response =>{
                this.client = response.data
                this.cardsCredit = response.data.cards.filter(a => a.type == "CREDIT")
                this.cardsDebit = response.data.cards.filter(a => a.type == "DEBIT")
            })
        },
        loadData(){
            axios.get("http://localhost:8080/api/accounts/"+this.id)
            .then(response =>{
                this.data = [...response.data.transactions]
                this.data = this.data.sort((a,b) => a.id - b.id)
                console.log(this.data)
                this.account =response.data
                console.log(this.account)
            })
        },
        logOut(){
            axios.post('/api/logout')
            .then(() => window.location.href="../index.html")
        },
        paramReading(){
            const URLsearchParams = new URLSearchParams(window.location.search)
            this.id = URLsearchParams.get("id")
        },

        
    }
}).mount("#app")


