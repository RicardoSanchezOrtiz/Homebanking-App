const {createApp} = Vue

createApp({
    data(){
        return{
            client: undefined,
            cardsCredit: undefined,
            cardsDebit: undefined,
            cardType: undefined,
            cardColor: undefined,
            number: undefined,
            reversedDate: undefined
        }
    },
    created(){
        this.loadData()
        this.expiredCard()
    },
    methods: {
        loadData(){
            axios.get("/api/clients/current")
            .then(response =>{
                this.client = response.data
                this.cardsCredit = response.data.cards.filter(a => a.type == "CREDIT" && a.active == true)
                this.cardsDebit = response.data.cards.filter(a => a.type == "DEBIT" && a.active == true)
            })
        },
        logOut(){
            axios.post('/api/logout')
            .then(() => window.location.href="../index.html")
        },
        expiredCard(){
            let expired = new Date();
            let options = {year: "numeric", month: "2-digit", day: "2-digit"};
            let formattedDate = expired.toLocaleString("es-ES",options);
            this.reversedDate = formattedDate.split("/").reverse().join("-");
          },
        hideCard(){
            axios.patch("/api/clients/current/cards", `cardNumber=${this.number}`,{headers:{'Content-Type':'application/x-www-form-urlencoded'}})
            .then(response => {
                console.log("Card successfully deleted")
                window.location.href="./cards.html"
            })
        },
    }
}).mount("#app")