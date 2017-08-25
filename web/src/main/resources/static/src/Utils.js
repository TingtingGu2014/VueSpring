/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


export function isEmpty(obj){
    return obj === null || typeof obj === 'undefined' || obj === '';
}

// The following three functions are from https://scotch.io/tutorials/easily-create-read-and-erase-cookies-with-jquery 
export function createCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires+"; path=/";
}

export function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

export function eraseCookie(name) {
    createCookie(name,"",-1);
}

export function getUserDetails(userUUID) {
    axios({
        method: 'get',
        dataType: 'json',
        url: '/userDetails/'+userUUID,
    }).then( (response) => {
        if(status === 200){
            alert("status is 200");
            return response.data;
        }
        else{
            alert("not 200");
            return;
        }     
    })
    .catch(function (error) {
        console.log(error);
    });
}

export function userProfileUpdate (object) {
    axios({
        method: 'post',
        dataType: 'json',
        url: '/api/userProfile/'+localStorage.userUUID,
    }).then( (response) => {
        if(response.status === 200){
            var data = response.data
            var details = data.userDetails
            var user = data.user
            if(!Utils.isEmpty(details) && !this.isUserDetailsFetched){
                this.setUserDetails(data.userDetails)
                this.major = details.major
                this.address = details.address
                this.affiliation = details.affiliation
                this.keyWords = ''
                this.city = details.city
                this.state = details.state
                this.zipcode = details.zipcode
            }                            
            this.email = user.email
            this.alias = user.alias
            this.userUUID = user.userUUID
            this.createdDate = user.createdDate
            this.firstName = user.firstName
            this.lastName = user.lastName                            
            this.setDetailsFetched(true)
        }
        else{
            alert("not 200");
            return;
        }     
    })
    .catch(function (error) {
        console.log(error);
    });
}

export function signUp(data, url) {
    axios({
        method: 'POST',
        url: url,
        dataType: 'json',
        data: data,
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
        },
    })
    .then( (response) => {
        var status = response.status;
        var data = response.data;

        if(status == 200){
            alert("200");
            localStorage.userEmail = data.email;
            localStorage.userName = data.alias;
            localStorage.userUUID = data.userUUID;
            document.location.href = '/home';
        }
        else{
            alert("Error status : " + status + "\nError message: "+response.message);
            return;
        }                                   
      })
      .catch( (error) => {
        console.log(error);
      });     
}

export function signIn(email, password) {
    axios({
        method: 'post',
        url: '/api/signIn',
        dataType: 'json',
        headers: {'X-Requested-With': 'XMLHttpRequest'},
        auth: {
            username: email,
            password: password,
        },
    })
    .then( (response) => {
        var status = response.status;
        if(status == 200 || status == "200"){
            localStorage.userInfo = JSON.stringify(response.data)      
            document.location.href = '/home'
        }
        else{
            alert("not 200");
            return;
        }                                   
    })
    .catch( (error) => {
        console.log(error);
    });      
}