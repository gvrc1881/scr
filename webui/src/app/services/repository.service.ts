import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { RepositoryModel } from '../models/repository.model';
@Injectable()
export class RepositoryService {
    myAppUrl: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json;charset=UTF-8',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }

    getAllRepositories() {
        return this.http.get<RepositoryModel[]>(environment.apiUrl + '/findAllRepositories', { headers: this.header });
    }

    getRepositoryById(id: number) {
        return this.http.get(environment.apiUrl + '/findRepositoryById/' + id, { headers: this.header });
    }

    existsRepositoryCode(repositoryCode: string) {
        return this.http.get(environment.apiUrl + '/existsRepositoryCode/' + repositoryCode, { headers: this.header });
    }

    existsRepositoryName(repositoryName: string) {
        return this.http.get(environment.apiUrl + '/existsRepositoryName/' + repositoryName, { headers: this.header });
    }

    existsRepositoryIp(repositoryIp: string) {
        return this.http.get(environment.apiUrl + '/existsRepositoryIp/' + repositoryIp, { headers: this.header });
    }

    AddRepository(repository) {
        return this.http.post(environment.apiUrl + '/addRepository', repository, { headers: this.header });
    }

    updateRepository(repository) {
        return this.http.put(environment.apiUrl + '/updateRepository', repository, { headers: this.header });
    }

    deleteRepository(id: string) {
        return this.http.delete(environment.apiUrl + '/deleteRepository/' + id, { headers: this.header });
    }
}