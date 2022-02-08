import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/services/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  @Output() login: EventEmitter<User> = new EventEmitter();
  userName: string= '';
  password: string= '';

  constructor(
    private userService: UserService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
  }
  
  // register(){
  //   const modalRef = this.modalService.open(RegisterComponent);
  // }

  signIn() {
    if (this.userName && this.password) {
      const user: User = new User();
      user.userName = this.userName;
      user.password = this.password;

      this.userService.login(user).subscribe( resp => {
        if (resp.success) {

          this.login.emit(resp.data);
          console.log("Successful Login")
        }
      }, error => {
        console.log(error.error.message);
      });
    } else {
      console.log('Enter username and password');
    }
  }

}
