import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';
import { CustomerService } from 'src/app/services/services/customer/customer.service';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent implements OnInit {

  constructor(private router:Router, private customerService:CustomerService) { }

  dateNow = new Date();

  ngOnInit(): void {
  }

  customer: Customer = {

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
      typeId: this.customer.typeId,
      numId: this.customer.numId,
      firstName: this.customer.firstName,
      lastName: this.customer.lastName,
      email: this.customer.email,
      birthdayDate: this.customer.birthdayDate,
      creationDate: formatDate(this.dateNow, 'YYYY/MM/dd', 'en-US')
    };

    this.customerService.save(data)
    .subscribe({
      next: ()=>{
      alert("El cliente ha sido creado exitosamente");
      this.router.navigate(["customers"])
    },

    error: (e) => console.error(e)
    })

  }

}
