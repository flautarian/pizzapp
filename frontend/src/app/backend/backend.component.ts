import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { WebSocketService } from '@services/WebSocketService';
import { environment } from 'environments/environment';


@Component({
    selector: 'app-backend',
    templateUrl: './backend.component.html',
    styleUrls: ['./backend.component.css']
})
export class BackendComponent {

    constructor(
        private http: HttpClient,
        private webSocketService: WebSocketService) { }

    orders: any[] = [];

    ngOnInit(): void {
        this.webSocketService.getUpdates().subscribe(update => {
            try{
                const index = this.orders.findIndex(order => order.id === update.id);
                if (index !== -1) {
                    this.orders[index] = update;
                } else {
                    this.orders.push(update);
                }
            }
            catch(e){
                console.error('Error:', e);
            }
        });
    }

    updateOrderStatus(id: string) {
        this.http.post<any>(`${environment.apiUrl}/order/orders/${id}`, { status: "DELIVERED" })
            .subscribe(
                data => {
                    alert("Order status updated successfully!");
                },
                error => {
                    console.error('Error:', error);
                    // Handle errors here
                }
            );
    }
}
