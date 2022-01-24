import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { UserService } from 'src/app/services/services/user/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  constructor(private router:Router, private userService:UserService) { }

  dateNow = new Date();

  ngOnInit(): void {
  }

  user: User = {

    //Tipo documento
    typeId: '',
    //Numero documento
    numId: '',
    //Nombre de usuario
    username: '',
    //Nombres
    firsName: '',
    //Apellidos
    lastName: '',
    //Correo
    email: '',
    //Fecha nacimiento
    birthdayDate: '',
    //Fecha creacion cuenta
    creationDate: '',

  };

  //Guardar usuario
  saveUser(): void{
    const data={
      typeId: this.user.typeId,
      numId: this.user.numId,
      username: this.user.username,
      firstName: this.user.firsName,
      lastName: this.user.lastName,
      email: this.user.email,
      birthdayDate: this.user.birthdayDate,
      creationDate: formatDate(this.dateNow, 'YYYY-MM-dd', 'en-US')
    };

    this.userService.createUser(data)
    .subscribe({
      next: ()=>{
      alert("El usuario ha sido creado exitosamete");
      this.router.navigate(["users"])
    },

    error: (e) => console.error(e)
    })

  }

}
