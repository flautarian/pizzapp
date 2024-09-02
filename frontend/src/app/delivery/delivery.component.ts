import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { WebSocketService } from '@services/WebSocketService';

@Component({
  selector: 'app-delivery',
  templateUrl: './delivery.component.html',
  styleUrls: ['./delivery.component.css']
})
export class DeliveryComponent {
  constructor(
    private http: HttpClient,
    private webSocketService: WebSocketService) { }

  orders: any[] = [];

  ngOnInit(): void {
    this.webSocketService.getUpdates().subscribe(update => {
      const index = this.orders.findIndex(order => order.id === update.id);
      if (index !== -1) {
        this.orders[index] = update;
      } else {
        this.orders.push(update);
      }
    });
  }

  updateOrderStatus(id: string) {
    this.http.post<any>(environment.apiUrl + '/orders/' + id, { status: "DELIVERED" })
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
