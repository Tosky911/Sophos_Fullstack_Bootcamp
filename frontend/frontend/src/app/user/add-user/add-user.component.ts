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
    //Nombres
    firstName: '',
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
  save(): void{
    const data={
      typeId: this.user.typeId,
      numId: this.user.numId,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      birthdayDate: this.user.birthdayDate,
      creationDate: formatDate(this.dateNow, 'YYYY/MM/dd', 'en-US')
    };

    this.userService.save(data)
    .subscribe({
      next: ()=>{
      alert("El cliente ha sido creado exitosamente");
      this.router.navigate(["users"])
    },

    error: (e) => console.error(e)
    })

  }

}
