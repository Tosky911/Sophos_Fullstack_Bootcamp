import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/user';
import { UserService } from 'src/app/services/services/user/user.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  users: User[] = [];
  currentUser: User ={};

  constructor(private service:UserService, private router:Router) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(){
    this.service.get().subscribe(resp=>{this.users= resp.data});
  }

  setActiveUser(user: User): void{
    this.currentUser = user;
  }

  EditUser(userId: number | undefined): void{
    this.router.navigate(["users/",userId,"products"]);
  }

}
