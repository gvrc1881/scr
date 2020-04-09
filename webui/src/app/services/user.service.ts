import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { User } from '../models/user.model';



@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(environment.apiUrl + '/users');
    }

    getById(_id: string) {
        return this.http.get(environment.apiUrl + '/users/' + _id);
    }

    create(user: User) {
        return this.http.post(environment.apiUrl + '/users/register', user);
    }

    update(user: User) {
        return this.http.put(environment.apiUrl + '/users/' + user._id, user);
    }

    delete(_id: string) {
        return this.http.delete(environment.apiUrl + '/users/' + _id);
    }
}