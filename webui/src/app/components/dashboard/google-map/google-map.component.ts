import { Component, OnInit } from '@angular/core';
//import { } from '@types/googlemaps/index';
//import { MouseEvent } from '@agm/core/agm-core';

@Component({
  selector: 'app-google-map',
  templateUrl: './google-map.component.html',
  styleUrls: ['./google-map.component.css'] 
})
export class GoogelMapComponent {
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  // google maps zoom level
 zoom: number = 8;
  
 // initial center position for the map
 lat: number = 51.673858;
 lng: number = 7.815982;
 previous;
 marker: string = "";
   
 clickedMarker(label: string, index: number, infowindow) {
   console.log(`clicked the marker: ${label || index}`)
this.marker = label;
   if (this.previous) {
     this.previous.close();
   }
   this.previous = infowindow;
 }
 
 mapClicked($event: any) {
   this.markers.push({
     lat: $event.coords.lat,
     lng: $event.coords.lng,
     draggable: true
   });
 }
 
 markerDragEnd(m: marker, $event: MouseEvent) {
   console.log('dragEnd', m, $event);
 }
 
 markers: marker[] = [
   {
     lat: 51.673858,
     lng: 7.815982,
     label: 'A',
     draggable: true
   },
   {
     lat: 51.373858,
     lng: 7.215982,
     label: 'B',
     draggable: false
   },
   {
     lat: 51.723858,
     lng: 7.895982,
     label: 'C',
     draggable: true
   }
 ]
}


// just an interface for type safety.
interface marker {
	lat: number;
	lng: number;
	label?: string;
	draggable: boolean;
}