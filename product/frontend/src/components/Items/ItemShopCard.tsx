import { Item } from "@/gql/graphql";
import { InformationCircleIcon } from "@heroicons/react/24/outline";
import { Button } from "../ui/Button";
import { Card, CardContent, CardFooter } from "../ui/Card";
import ItemCountdown from "./ItemCountdown";
import ItemStockBadge from "./ItemStockBadge";

function ItemShopCard({ item }: { item: Item }) {
  return (
    <Card
      key={item.id}
      className="min-w-350px group relative flex flex-col overflow-hidden transition-shadow hover:shadow-lg"
    >
      <CardContent className="flex-grow p-4">
        <div className="mb-2 flex items-start justify-between">
          <h3 className="group-hover:text-primary text-xl font-semibold transition-colors">
            {item.name} - £{item.price}
          </h3>
          <div className="hidden md:block">
            {item.stock !== undefined && (
              <ItemStockBadge stock={item?.stock ?? 0} />
            )}
          </div>
        </div>
        <p className="text-muted-foreground mb-4 text-sm">{item.description}</p>
        <p>
          Ending:{" "}
          {item.endingTime && <ItemCountdown endingTime={item.endingTime} />}
        </p>
      </CardContent>
      <CardFooter className="flex justify-end p-4 pt-0">
        <Button variant="outline" onClick={() => null}>
          Details
          <InformationCircleIcon className="ml-2 h-4 w-4" />
        </Button>
      </CardFooter>
    </Card>
  );
}

export default ItemShopCard;
