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
            if(!isEmpty(details) && !this.isUserDetailsFetched){
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
            localStorage.userInfo = JSON.stringify(data)
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

export function isAdminUser () {
    var user = null
    try{
        user = JSON.parse(localStorage.userInfo)
    }
    catch(err) {
        console.log(err.message)
    }
    if(!isEmpty(user)){
        var userRoles = user.roles
        if(!isEmpty(userRoles)) {
            var userRolesArr = userRoles.split(",");
            if(null != userRolesArr && userRolesArr.indexOf("admin") >= 0){
                return true;
            }
        }
    }
    return false;
}

export function getErrorPage(code, errorMessage) {
    var errors = require('./ErrorInfo')
    var errorsObj = errors['default']
    var error = errorsObj[code]
    if(!isEmpty(errorMessage)){
        error['message'] = error['message'] + "<br><b> Error message: </b>" + errorMessage
    }
    return error
}

export function loadErrorPages(status, message){
    
}

/**
 * Compare and return the differences of two string arrays
 * 
 * @param {type} strArr1 : string array to be compared
 * @param {type} strArr2 : string array for comparing
 * @returns {undefined}
 */
export function getArraydifferences(strArr1, strArr2){
    var strArrSorted1 = strArr1.sort()
    var strArrSorted2 = strArr2.sort()
    var res1 = []
    var res2 = []
    var l = 0
    var k = 0
    var i = 0
    var j = 0
    while(i < strArrSorted1.length) {
        while(j < strArrSorted2.length) {
            while(strArrSorted1[i] < strArrSorted2[j]){
                res1[k] = strArrSorted1[i]
                k++
                i++                
            }              
            if(strArrSorted1[i] == strArrSorted2[j]){                
                i++
                j++
                continue
            }
            while(strArrSorted1[i] > strArrSorted2[j]){
                res2[l] = strArrSorted2[j]
                l++
                j++
            }
        }
        res1[k] = strArrSorted1[i]
        k++
        i++
    }
    return res1
}

export function getAllUserRoles() {
    return axios({
        method: 'get',
        url: '/api/admin/roles',
        dataType: 'json',
        headers: {'X-Requested-With': 'XMLHttpRequest'},
    })
    .then( (response) => {
        var status = response.status;
        if(status == 200 || status == "200"){  
            var roleNames = ''
            var roles = response.data
            for(var i = 0; i < roles.length; i++){
                if(roles[i].hasOwnProperty("roleName")){
                    roleNames += roles[i].roleName + ","
                }
            }
            roleNames = roleNames.substr(0,roleNames.length-1)
            return roleNames
        }
        else{
            alert("status " + status + ", cannot get the roles!");
        }                                   
    })
    .catch( (error) => {
        console.log(error);
    }); 
}