import { Injectable } from "@angular/core";
import { User } from "src/app/entities/user";

@Injectable({
    providedIn: 'root'
})
export class GlobalService {
    public user: User = new User();
    constructor(){
        
    }
}