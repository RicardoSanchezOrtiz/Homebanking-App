const{createApp} = Vue

createApp({
    data(){
        return{
            id: 0, 
            loanId: 0,
            amount: 0, 
            payments: 0, 
            accountDestiny: "" ,
            loans: undefined,
            name: "",
            maxAmount: null,
            NumberOfPayments: null,
            payOptions: [],
            interest: null,
            USDollar: new Intl.NumberFormat('en-US', {
                style: 'currency',
                currency: 'USD',
            })
        }
        
    },
    created(){
        this.loadData()
        this.loadLoans()
    },
    methods: {
        loadData(){
            axios.get("/api/clients/current")
            .then(response =>{
                this.client = response.data
                this.accounts = response.data.accounts.filter(a => a.active == true)
            })
        },
        applyLoan(e){
            e.preventDefault()
            axios.post("/api/loans"
            , { 
                "id": this.loans[this.id].id,
                "amount": this.amount,
                "payments": this.payments,
                "accountDestiny": this.accountDestiny
            })
            .then(() => {
                window.location.href = '../web/accounts.html'
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
        
        deletePayments(){
            this.payOptions = []
        },
        createLoan(){
            axios.post("/api/loans/newloan"
            , {     
                "name": this.name,
                "maxAmount": this.maxAmount,
                "payments": this.payOptions,
                "interest": this.interest
            },)
            .then(() => {
                console.log("success")
            })
        },
        addNumberPayments(number){
            this.payOptions.push(number)
            this.NumberOfPayments = null;
        },
        loadLoans(){
            axios.get("http://localhost:8080/api/loans")
            .then(response =>{
                this.loans = [...response.data]
                console.log(this.loans)
            })
        },
        logOut(){
            axios.post('/api/logout')
            .then(() => window.location.href="../index.html")
        }
    }
}).mount("#app")
