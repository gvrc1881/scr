import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { TrackModel } from '../models/track.model';

@Injectable()
export class TrackService {
    myAppUrl: string = environment.apiUrl;
    header: any;
    accessToken: any = JSON.parse(localStorage.getItem('accessToken'));
    constructor(private http: HttpClient) {
        this.header = new HttpHeaders({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    
    findAllTrack () {
        return this.http.get<TrackModel[]>(this.myAppUrl + '/findAllTrack', { headers: this.header });
    }

    saveTrack (track) {
    console.log('json object::'+JSON.stringify(track));
        return this.http.post(this.myAppUrl + "/addTrack",track,{ headers: this.header});
    }

    findTrackById(id: number){
        return this.http.get(this.myAppUrl + "/findTrack/" +id , { headers : this.header });
    }

    updateTrack (track) {
        console.log('update service:::'+track);
        return this.http.put(this.myAppUrl + "/updateTrack",track,{ headers: this.header});
    }

    deleteTrackById(id: number) {
        return this.http.delete(this.myAppUrl + "/deleteTrack/" +id ,{headers: this.header});
    }


}