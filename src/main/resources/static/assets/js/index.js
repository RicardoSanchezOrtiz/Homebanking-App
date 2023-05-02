const {createApp} = Vue

createApp({
    data(){
        return{
            signup: undefined,
            firstName: '',
            lastName: '',
            email: '',
            password:'',
            error: undefined
        }
    },
    created(){
        this.signup = true
    },
    methods: {
        login(e){
            e.preventDefault()
            axios.post('/api/login',`email=${this.email}&password=${this.password}`
            ,{headers:{'Content-Type':'application/x-www-form-urlencoded'}
            })
            .then(() => window.location.href = "/web/accounts.html")
            .catch(error => {
                this.error = "User not found"
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
        register(){
            axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`
            ,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(() => {
                console.log("success")})
            .catch(error => {
                this.error = error.response.data
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
        }
}
}).mount("#app")